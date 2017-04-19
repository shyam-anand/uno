const React = require('react');

import Uno from './uno';
import BusinessForm from './business';

class PageList extends React.Component {

    constructor(props) {
        super(props);
        this.page = props.page;

        this.state = {
            img: '',
            connected: false,
            agent: null
        };

        this.onListItemClicked = this.onListItemClicked.bind(this);
    }

    componentDidMount() {
        FB.api(
            `/${this.page.id}/picture?height=42&width=42`,
            function (response) {
                if (response && !response.error) {
                    this.setState({
                        img: response.data.url
                    });
                }
            }.bind(this)
        );

        Uno.api(`/facebook/pages/${this.page.id}/agent`, function (response, status, xhr) {
            console.log(response, `${this.page.name}/agent`);
            if (response) {
                this.setState({
                    connected: true,
                    agent: response
                })
            }
        }.bind(this));
    }

    onListItemClicked() {
        this.props.onPageSelected(this.page);
    }

    render() {
        console.log("Rendering PageList");
        return (
            <li className="collection-item avatar" onClick={this.onListItemClicked}>
                <img src={this.state.img} alt="" className="square"/>
                <strong className="title">{this.page.name}</strong>

                <p className="grey-text">{this.page.category}</p>
                {this.state.connected ?
                    <a href="#!" className="secondary-content">{this.state.agent.name}</a> : ''}

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
            accessToken: props.accessToken,
            loading: true,
            pages: [],
            connectedPages: [],
            editingBusiness: false
        };
    }


    pageSelected(page) {
        console.log(page, "page selected");
        this.setState({
            selectedPage: page,
            editingBusiness: true
        })

    }

    setConnectedPages() {
        console.log("Setting connected pages");

        var pages = this.state.pages;
        var connectedPages = this.state.connectedPages;

        Uno.api(`/${this.uid}/businesses`, function (businesses) {
            console.log(businesses, "user's businesses");
            pages.forEach(function (page, i) {
                var j = $.inArray(page.id, businesses);
                if (j > -1 && businesses[j].agent != null) {
                    connectedPages.push(page, id);
                    pages[i].connected = true;
                } else {
                    pages[i].connected = false;
                }
            }.bind(this))
        });

        console.log(this.state.pages, "After setConnectedPages");
    }

    componentDidMount() {
        if (this.state.pages.length > 0) {
            this.setState({
                loading: false
            });
        } else {
            this.setState({
                loading: true
            });
            this.FB.api(`/${this.uid}/accounts`, function (response) {
                console.log(response, "/accounts");
                if (!response || response.error) {
                    if (response.error) {
                        console.error(response.error);
                    }
                } else {
                    var pages = response.data;

                    this.setState({
                        pages: pages
                    });

                    pages.map((page) => {
                        Uno.saveFBPage({
                            id: page.id,
                            name: page.name,
                            access_token: page.access_token,
                            user: {
                                id: this.uid
                            }
                        }, function (response) {
                            console.log(response, "saveFBPage")
                        });
                    });

                }

                this.setState({
                    loading: false
                })
            }.bind(this));

        }
    }

    businessSaved(business, page) {
        console.log(business, "businessSaved");
        console.log(page, "businessSaved");

        this.FB.api(`/${page.id}/subscribed_apps?access_token=${this.state.accessToken}`, 'post', {access_token: page.access_token}, function (response) {
            if (!response || response.error) {
                console.error(response.error.message, "subscribed_apps error");
            } else {
                this.setState({
                    agentConnected: status,
                    editingBusiness: false
                });
                console.log(response, "Subscribed to Page");
            }
        }.bind(this));
    }

    cancelBusinessForm() {
        this.setState({editingBusiness: false});
    }

    render() {
        console.log(this.state.pages, "Rendering Pages");
        if (this.state.loading) {
            return (
                <div className="container full-height">
                    <div className="row">
                        <div className="progress col s4 offset-s4">
                            <div className="indeterminate"></div>
                        </div>
                    </div>
                </div>
            );
        }
        return (
            <div className="valign-wrapper">
                {
                    this.state.editingBusiness ?
                        <BusinessForm page={this.state.selectedPage} fbUserId={this.uid}
                                      businessSaved={this.businessSaved.bind(this)}
                                      cancel={this.cancelBusinessForm.bind(this)}/>
                        :
                        <div className="full-height full-width">
                            <div className="center section">
                                <h5 className="light">Select the page to connect to Uno</h5>
                            </div>
                            <div className="section">
                                <ul className="collection links-list center-block no-border pages-ul">
                                    {this.state.pages.map((page) =>
                                        <PageList key={page.id} onPageSelected={this.pageSelected.bind(this)}
                                                  page={page}/>)}
                                </ul>
                            </div>
                        </div>
                }

            </div>
        )
    }
}