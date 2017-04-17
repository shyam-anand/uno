const React = require('react');

import Uno from './uno';
class PageList extends React.Component {

    constructor(props) {
        super(props);
        this.page = props.page;

        this.categories = this.getCategories().bind(this);

        this.onListItemClicked = this.onListItemClicked.bind(this);
    }

    getCategories() {
        Uno.api("/businesses/categories", function (response) {
            if (response.success) {
                this.categories = response.data;
            }
        }.bind(this))
    }

    onListItemClicked(e) {
        this.props.onPageSelected(this.page);
    }

    render() {
        console.log("Rendering PageList");
        return (
            <li className="collection-item avatar" onClick={this.onListItemClicked}>
                <span className="title strong">{this.page.name}</span>

                <div className="grey-text">{this.page.category}</div>
                {this.page.connected == true ?
                    <span className="secondary-content"><i className="material-icons green-text">done</i></span>
                    : ''}
            </li>
        );
    }
}

export default class Pages extends React.Component {
    constructor(props) {
        super(props);

        this.FB = props.fb;
        this.uno = new Uno();
        this.uid = props.uid;
        this.state = {
            pages: [],
            connectedPages: [],
            editingBusiness: false
        };

        this.pageSelected = this.pageSelected.bind(this);
        console.log("Initialised pages. uid: " + this.uid)
    }

    pageSelected(page) {
        console.log(page, "page selected");
        if (page.connected == false) {
            this.setState({selectedPage: page});

            this.FB.api(
                `/${page.id}/subscribed_apps`,
                'post',
                {access_token: page.access_token},
                this.pageSubscribed.bind(this));
        } else {
            console.log("TO BE DISCONNECTED");
        }
    }

    pageSubscribed(response) {
        if (!response || response.error) {
            console.error(response.error.message, "subscribed_apps error");
        } else {
            Uno.fbSubscribeToPage({
                id: this.state.selectedPage.id,
                name: this.state.selectedPage.name,
                access_token: this.state.selectedPage.access_token,
                user: {
                    id: this.uid
                }
            }, this.setConnectedPages.bind(this));
            console.log(response, "Subscribed to Page");
        }
    }

    setConnectedPages() {
        console.log("Setting connected pages");

        var pages = this.state.pages;
        var connectedPages = this.state.connectedPages;

        Uno.api(`/facebook/pages/${this.uid}`, function (response) {
            console.log(response, "Connected pages");
            if (response.success) {
                var subscribedPages = response.data;
                pages.forEach(function (page, i) {
                    if ($.inArray(page.id, subscribedPages) > -1) {
                        connectedPages.push(page.id);
                        pages[i].connected = true;
                    } else {
                        pages[i].connected = false;
                    }
                }, this)
            }
            this.setState({
                pages: pages,
                connectedPages: connectedPages
            });
        }.bind(this));

        console.log(this.state.pages, "After setConnectedPages");
    }

    componentWillMount() {
        this.FB.api(`/${this.uid}/accounts`, function (response) {
            console.log(response, "/accounts");
            if (response && !response.error) {
                var pages = response.data;
                var connectedPages = [];

                pages.forEach((page, i) => {
                    FB.api(
                        `/${page.id}/picture?height=32&width=32`,
                        function (response) {
                            if (response && !response.error) {
                                pages[i].img = response.data.url;
                            }
                        }.bind(this)
                    )
                });

                Uno.api(`/facebook/pages/${this.uid}`, function (response) {
                    console.log(response, "Connected pages");
                    if (response.success) {
                        var subscribedPages = response.data;
                        pages.forEach(function (page, i) {
                            if ($.inArray(page.id, subscribedPages) > -1) {
                                connectedPages.push(page.id);
                                pages[i].connected = true;
                            } else {
                                pages[i].connected = false;
                            }
                        }, this)
                    }
                    this.setState({
                        pages: pages,
                        connectedPages: connectedPages
                    });

                }.bind(this));

            } else if (response.error) {
                console.error(response.error);
            }
        }.bind(this));
    }

    render() {
        console.log(this.state.pages, "Rendering Pages");
        return (
            <div className="valign-wrapper">
                {
                    this.state.editingBusiness ?
                        <div className="full-height full-width">
                            <div className="row">
                                <form className="col s12">
                                    <div className="row">
                                        <div className="input-field col s6">
                                            <input placeholder="Business Name" id="business_name" type="text"
                                                   className="validate" value={this.state.selectedPage.name}/>
                                            <label for="first_name">Business Name</label>
                                        </div>
                                        <div className="input field col s6">
                                            <select id="business_category">
                                                <option value="" disabled selected>Choose...</option>
                                                {
                                                    this.categories.map((cat) => <option
                                                        value="{cat.id}">{cat.name}</option>)
                                                }
                                            </select>
                                            <label>Category</label>
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div class="input-field col s12">
                                            <textarea id="business_desc" class="materialize-textarea"
                                                      placeholder="Tell us about your business"></textarea>
                                            <label for="textarea1">Description</label>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        :
                        <div className="full-height full-width">
                            <div className="center">
                                <h5 className="light">Select Pages</h5>

                                <p>Just click on one, and Uno will be connected to it.</p>
                            </div>
                            <ul className="collection links-list half-width center-block">
                                {this.state.pages.map((page) => <PageList key={page.id}
                                                                          onPageSelected={this.pageSelected}
                                                                          page={page}/>)}
                            </ul>
                        </div>
                }

            </div>
        )
    }
}