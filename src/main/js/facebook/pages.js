const React = require('react');

import Uno from './uno';
import BusinessForm from './business';

class PageList extends React.Component {

    constructor(props) {
        super(props);
        this.page = props.page;

        this.onListItemClicked = this.onListItemClicked.bind(this);
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
            this.setState({
                selectedPage: page,
                editingBusiness: true
            })
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
                connectedPages: connectedPages,
                editingBusiness: true
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
            businessSaved: status,
            editingBusiness: false
        })
    }

    render() {
        console.log(this.state.pages, "Rendering Pages");
        return (
            <div className="valign-wrapper">
                {
                    this.state.editingBusiness ?
                        <BusinessForm page={this.state.selectedPage} fbUserId={this.uid}
                                      saveBusiness={this.saveBusiness.bind(this)}/>
                        :
                        <div className="full-height full-width">
                            <div className="center">
                                <h5 className="light">Select Pages</h5>
                            </div>
                            <ul className="collection links-list half-width center-block">
                                {this.state.pages.map((page) =>
                                    <PageList key={page.id} onPageSelected={this.pageSelected} page={page}/>)}
                            </ul>
                        </div>
                }

            </div>
        )
    }
}