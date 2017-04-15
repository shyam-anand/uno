const React = require('react');

class PageList extends React.Component {

    constructor(props) {
        super(props);
        this.page = props.page;
        this.onListItemClicked = this.onListItemClicked.bind(this);
    }

    onListItemClicked(e) {
        this.props.onPageSelected(this.page);
    }

    render() {
        return (
            <li className="collection-item" onClick={this.onListItemClicked}>
                <span className="strong">{this.page.name}</span>

                <div className="grey-text">{this.page.category}</div>
            </li>
        );
    }
}

export default class Pages extends React.Component {
    constructor(props) {
        super(props);

        this.FB = props.fb;
        this.uid = props.uid;
        this.state = {
            pages: []
        };

        this.pageSelected = this.pageSelected.bind(this);
        this.getPages = this.getPages.bind(this);
        console.log("Initialised pages. uid: " + this.uid)
    }

    pageSelected(page) {
        console.log(page, "page selected");
        this.setState.selectedPage = page.id;

        this.FB.api(`/${page.id}/subscribed_apps`, 'post', {access_token: page.access_token}, function (response) {
            if (!response || response.error) {
                console.error(response.error.message, "subscribed_apps error");
            } else {
                console.log(response, "Subscribe to Page");
            }
        })
    }

    componentWillMount() {
        var accountsApi = `/${this.uid}/accounts`;
        this.FB.api(accountsApi, this.getPages)
    }

    getPages(response) {
        console.log(response, "/accounts");
        if (response && !response.error) {
            this.setState({
                pages: response.data
            });
        } else if (response.error) {
            console.error(response.error);
        }
        console.log(this.state.pages, "pages");
    }

    render() {
        console.log(this.state.pages, "Pages.render");
        return (
            <div>
                <h5 className="light">Select the page to be connected</h5>
                <ul className="collection links-list half-width">
                    {this.state.pages.map((page) => <PageList key={page.id} onPageSelected={this.pageSelected}
                                                              page={page}/>)}
                </ul>
            </div>
        )
    }
}