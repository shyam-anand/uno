const React = require('react');

import Uno from './uno';

class BusinessForm extends React.Component {

    constructor(props) {
        this.page = props.page;
        this.categories = props.categories;
        this.saveBusiness = props.saveBusiness;
        this.state = {
            name: this.page.name,
            category: '',
            description: '',
            address: ''
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        const target = e.target;
        const value = target.type == 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            [name]: value
        })
    }

    handleSubmit(e) {
        e.preventDefault();

        Uno.api("/businesses", 'POST', {
            name: this.state.name,
            category: this.state.category,
            description: this.state.description,
            address: this.state.address
        }, function (response) {
            if (response.success) {
                this.saveBusiness(true);
            }
        }.bind(this))
    }

    render() {
        return (
            <div className="full-height full-width">
                <div className="row">
                    <form className="col s12">
                        <div className="row">
                            <div className="input-field col s6">
                                <input placeholder="Business Name" id="name" type="text" onChange={this.handleChange}
                                       className="validate" value={this.state.name}/>
                                <label for="first_name">Business Name</label>
                            </div>
                            <div className="input field col s6">
                                <select id="category" value={this.state.category} onChange={this.handleChange}>
                                    <option value="" disabled selected>Choose...</option>
                                    {
                                        this.categories.map((cat) =>
                                            <option value="{cat.id}">{cat.name}</option>)
                                    }
                                </select>
                                <label>Category</label>
                            </div>
                        </div>
                        <div className="row">
                            <div className="input-field col s12">
                                            <textarea id="description" className="materialize-textarea"
                                                      onChange={this.handleChange}
                                                      placeholder="Tell us about your business"
                                                      value={this.state.description}></textarea>
                                <label for="description">Description</label>
                            </div>
                        </div>
                        <div className="row">
                            <div className="input-field col s12">
                                <textarea id="address" className="materialize-textarea" onChange={this.handleChange}
                                          placeholder="" value={this.state.address}></textarea>
                                <label for="address">Address</label>
                            </div>
                        </div>
                    </form>
                </div>
                <div className="row">
                    <div className="col s2 offset-s10">
                        <button class="btn waves-effect waves-light blue darken-4" type="submit" name="save_business">
                            Submit
                        </button>
                    </div>
                </div>
            </div>
        );
    }
}

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

    onListItemClicked() {
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

    saveBusiness(status) {
        this.setState({
            businessSaved: status
        })
    }

    render() {
        console.log(this.state.pages, "Rendering Pages");
        return (
            <div className="valign-wrapper">
                {
                    this.state.editingBusiness ?
                        <BusinessForm page={this.state.selectedPage} categories={this.categories}
                                      saveBusiness={this.saveBusiness.bind(this)}/>
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