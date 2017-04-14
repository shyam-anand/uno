const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            FB: null
        }
    }

    componentWillMount() {
        window.fbAsyncInit = function () {
            FB.init({
                appId: '249750865488314',
                xfbml: true,
                version: 'v2.8'
            });
            FB.AppEvents.logPageView();

            FB.getLoginStatus(function (response) {
                console.log("FB login status: " + response.status);
            });
        };

        (function (d, s, id) {
            var js, fjs = d.getElementsByTagName(s)[0];
            if (d.getElementById(id)) {
                return;
            }
            js = d.createElement(s);
            js.id = id;
            js.src = "//connect.facebook.net/en_US/sdk.js";
            fjs.parentNode.insertBefore(js, fjs);
        }(document, 'script', 'facebook-jssdk'));
    }

    componentDidMount() {
    }

    render() {
        return (
            <h1>This is React</h1>
        )
    }
}

ReactDOM.render(<App/>, document.getElementById("react-app"));