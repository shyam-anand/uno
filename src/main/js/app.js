const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

import Facebook from './facebook/facebook.js';

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: ''
        };
    }

    setUserName(username) {
        this.setState({
            username: username
        });
    }

    render() {
        return (
            <div>
                <div className="row">
                    <div className="col s6">
                        <span className="logo-text">UNO</span>
                    </div>
                    <div className="col s2 offset-s4 valign-wrapper">
                        <p className="grey-text strong ">{this.state.username}</p>
                    </div>
                </div>
                <Facebook fb={this.props.fb} setUsername={this.setUserName.bind(this)}/>
            </div>
        );
    }
}

ReactDOM.render(<App fb={FB}/>, document.getElementById("react-app"));