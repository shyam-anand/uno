const React = require('react');

import Uno from './uno';
import Pages from './pages.js';

export default class Facebook extends React.Component {

    constructor(props) {
        super(props);

        this.FB = props.fb;
        this.hasPagesPerms = false;

        Uno.api("/facebook/config", function (response) {
            console.log(response, "FB config");
            if (response.success) {
                this.appId = response.data.appId;
                this.version = response.data.version;

                this.FB.init({
                    appId: this.appId,
                    xfbml: false,
                    version: this.version
                });
            }
        }.bind(this));

        this.state = {
            fbLoginStatus: false,
            accessToken: '',
            uid: '',
            name: ''
        };

        this.fbLogin = this.fbLogin.bind(this);
        this.onStatusChange = this.onStatusChange.bind(this);
        this.getPermissions = this.getPermissions.bind(this);

    }

    componentWillMount() {
        //FB.AppEvents.logPageView();

        this.FB.getLoginStatus(this.onStatusChange);
    }

    componentDidMount() {
        this.FB.Event.subscribe('auth.logout', this.onLogout.bind(this));
        this.FB.Event.subscribe('auth.statusChange', this.onStatusChange);
    }

    onStatusChange(response) {
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
                fbLoginStatus: false
            });
        }
    }

    userProfile(response) {
        console.log(response, "/me");
        this.setState({
            name: response.name
        });
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
                    hasPagesPerms: true
                })
            } else {
                this.setState({
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
        return (
            <div>{
                this.state.fbLoginStatus === false
                    ?
                    <div className="container center valign-wrapper full-height">
                        <div className="center-block">
                            <h2 className="thin">Hello</h2>

                            <h4 className="thin">Let's build bots!</h4>

                            <p className="grey-text text-lighten-2">Or, make humans obsolete, to put it bluntly.</p>
                            <a className="btn blue darken-4" onClick={this.fbLogin}>Sign in with Facebook</a>
                        </div>
                    </div>
                    :
                    <div className="section">
                        <div className="row">
                            <div className="col s2 offset-s10">
                                <p className="center">
                                    <span className="grey-text text-darken-2 bold">{this.state.name}</span>
                                </p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col s12">
                                {
                                    this.state.hasPagesPerms == true ?
                                        <Pages fb={this.FB} uid={this.state.uid}/> :
                                        <p className="center"><a className="btn blue darken-4"
                                                                 onClick={this.getPermissions}>
                                            Give permission to manage pages
                                        </a></p>
                                }
                            </div>
                        </div>
                    </div>
            }</div>
        )
    }
}