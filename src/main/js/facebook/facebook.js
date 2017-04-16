const React = require('react');

import Uno from './uno';
import Pages from './pages.js';

export default class Facebook extends React.Component {

    constructor(props) {
        super(props);

        this.uno = new Uno();
        this.FB = props.fb;
        this.hasPagesPerms = false;

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
        this.FB.init({
            appId: '253910318405702',
            xfbml: false,
            version: 'v2.8'
        });
        //FB.AppEvents.logPageView();

        this.FB.getLoginStatus(this.onStatusChange);
    }

    componentDidMount() {
        this.FB.Event.subscribe('auth.logout', this.onLogout.bind(this));
        this.FB.Event.subscribe('auth.statusChange', this.onStatusChange);
    }

    onStatusChange(response) {
        console.log(response, "onStatusChange");
        var self = this;

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
        this.uno.fbLogin({
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
                    ? <div className="container ">
                    <h2 className="thin">Hello! Get started by logging in using Facebook.</h2>

                    <p className="center">
                        <a className="btn blue darken-4" onClick={this.fbLogin}>Login to Facebook</a>
                    </p>
                </div>
                    : <div className="row">
                    <div className="col s2 left-pane">
                        <p className="center">
                            <span className="grey-text text-darken-2 bold">{this.state.name}</span>
                        </p>
                    </div>
                    <div className="col s10">
                        {
                            this.state.hasPagesPerms == true
                                ? <Pages fb={this.FB} uid={this.state.uid}/>
                                : <p>
                                <a className="btn blue darken-4" onClick={this.getPermissions}>Give permission to manage
                                    pages</a>
                            </p>
                        }
                    </div>
                </div>
            }</div>
        )
    }
}