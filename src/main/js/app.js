const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

    constructor(props) {
        super(props);

        this.FB = props.FB;

        this.state = {
            fbLoginStatus: false,
            message: ''
        }
    }

    componentWillMount() {


        this.FB.init({
                appId: '249750865488314',
            xfbml: false,
                version: 'v2.8'
            });
        //FB.AppEvents.logPageView();

        this.FB.getLoginStatus(function (response) {
                console.log("FB login status: " + response.status);
            this.state.fbLoginStatus = response.status == 'connected';
            });


        //(function (d, s, id) {
        //    var js, fjs = d.getElementsByTagName(s)[0];
        //    if (d.getElementById(id)) {
        //        return;
        //    }
        //    js = d.createElement(s);
        //    js.id = id;
        //    js.src = "//connect.facebook.net/en_US/sdk.js";
        //    fjs.parentNode.insertBefore(js, fjs);
        //}(document, 'script', 'facebook-jssdk'));
    }

    componentDidMount() {

        this.FB.Event.subscribe('auth.logout',
            this.onLogout.bind(this));
        this.FB.Event.subscribe('auth.statusChange',
            this.onStatusChange.bind(this));
    }

    onStatusChange(response) {
        console.log(response);
        var self = this;

        if (response.status === "connected") {
            this.FB.api('/me', function (response) {
                var message = "Welcome " + response.name;
                self.setState({
                    message: message
                });
            })
        }
    }

    onLogout(response) {
        this.setState({
            message: ""
        });
    }

    fbLogin() {
        FB.login(function (response) {
            console.log("Login response - " + response, status);
            if (response.status == 'connected') {
                this.state.fbLoginStatus = true;
            }
        }, {scope: 'public_profile,email'});
    }

    render() {
        return (
            <div>{
                this.state.fbLoginStatus === false ?
                    <a className="btn blue-darken-4" onClick={this.fbLogin}>Login to Facebook</a> :
                    <span className="strong">{this.state.message}</span>
            }</div>
        )
    }
}

ReactDOM.render(<App fb={FB}/>, document.getElementById("react-app"));