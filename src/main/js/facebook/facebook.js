const React = require('react');

import Uno from './uno';
import Pages from './pages.js';

export default class Facebook extends React.Component {

    constructor(props) {
        super(props);

        this.FB = props.fb;
        this.hasPagesPerms = false;

        this.state = {
            loading: true,
            fbLoginStatus: false,
            accessToken: '',
            uid: '',
            name: ''
        };

        this.fbLogin = this.fbLogin.bind(this);
        this.onStatusChange = this.onStatusChange.bind(this);
        this.getPermissions = this.getPermissions.bind(this);

        Uno.api("/facebook/config", function (response, status, xhr) {
            console.log(response, "FB config");
            if (status == 'success' && response.success) {
                this.appId = response.data.appId;
                this.version = response.data.version;

                this.FB.init({
                    appId: this.appId,
                    xfbml: false,
                    version: this.version
                });

                this.FB.getLoginStatus(this.onStatusChange);
            } else {
                console.error(xhr, 'FB init failed');
            }
        }.bind(this));

    }

    componentWillMount() {
        //FB.AppEvents.logPageView();
    }

    componentDidMount() {
        this.FB.Event.subscribe('auth.logout', this.onLogout.bind(this));
        this.FB.Event.subscribe('auth.statusChange', this.onStatusChange.bind(this));
    }

    onStatusChange(response) {
        this.setState({
            loading: true
        });
        console.log(response, "onStatusChange");

        if (response.status === "connected") {
            this.FB.api('/me', this.userProfile.bind(this));

            this.setState({
                fbLoginStatus: true,
                accessToken: response.authResponse.accessToken,
                uid: response.authResponse.userID
            });

            this.FB.api(
                `/${this.state.uid}/permissions`,
                this.pagesPerms.bind(this)
            );
        } else {
            this.setState({
                loading: false,
                fbLoginStatus: false
            });
        }
    }

    userProfile(response) {
        console.log(response, "/me");
        this.setState({
            name: response.name
        });
        this.props.setUsername(this.state.name);
        Uno.fbLogin({
            id: this.state.uid,
            name: this.state.name
        }, function (response) {
            console.log(response, "Uno fbLogin response");
        });
    }

    pagesPerms(response) {
        console.log(response, "permission check");
        if (response && !response.error) {
            var manage_pages = false;
            var pages_messaging_subscriptions = false;
            var pages_messaging = false;
            var pages_messaging_phone_number = false;

            response.data.map((perm) => {
                if (perm.status == 'granted') {
                    if (perm.permission == 'manage_pages') {
                        manage_pages = true;
                    } else if (perm.permission == 'pages_messaging_phone_number') {
                        pages_messaging_phone_number = true;
                    } else if (perm.permission == 'pages_messaging') {
                        pages_messaging = true;
                    } else if (perm.permission == 'pages_messaging_subscriptions') {
                        pages_messaging_subscriptions = true;
                    }
                }
            });

            if (manage_pages && pages_messaging && pages_messaging_subscriptions) {
                this.setState({
                    loading: false,
                    hasPagesPerms: true
                })
            } else {
                this.setState({
                    loading: false,
                    hasPagesParams: false
                })
            }
        }
    }

    onLogout(response) {
        this.setState({
            message: "Logged out [" + response.status + "]"
        });
    }

    fbLogin() {
        this.FB.login(this.onStatusChange, {scope: 'public_profile, email, manage_pages, publish_pages, pages_messaging, pages_messaging_subscriptions, pages_messaging_phone_number'});
    }

    getPermissions() {
        FB.login(this.onStatusChange, {scope: 'public_profile, email, manage_pages, publish_pages, pages_messaging, pages_messaging_subscriptions, pages_messaging_phone_number'});
        return false;
    }

    render() {
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
        } else {
            return (
                <div className="container">{
                    this.state.fbLoginStatus === false
                        ?
                        <div className="container center valign-wrapper full-height">
                            <div className="center-block">
                                <h2 className="thin">Hello.</h2>
                                <h4 className="thin">Let's build bots!</h4>
                                <a className="btn-flat blue darken-4 white-text" onClick={this.fbLogin}>Sign in with
                                    Facebook</a>
                            </div>
                        </div>
                        :
                        <div className="row">
                            <div className="col s12">
                                {
                                    this.state.hasPagesPerms == true ?
                                        <Pages fb={this.FB} uid={this.state.uid}
                                               accessToken={this.state.accessToken}/> :
                                        <div className="valign-wrapper full-height full-width">
                                            <div className="center-block center">
                                                <p>
                                                    Uno requires permission to manage your pages, so that it can
                                                    connect
                                                    to it and talk to users who message your page.
                                                </p>

                                                <p>
                                                    We won't post anything without your permission.
                                                </p>
                                                <a className="btn blue darken-4" onClick={this.getPermissions}>
                                                    Give permission to manage pages
                                                </a>
                                            </div>
                                        </div>
                                }
                            </div>
                        </div>

                }</div>
            )
        }
    }
}