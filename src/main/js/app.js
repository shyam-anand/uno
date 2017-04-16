const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

import Facebook from './facebook/facebook.js';

class App extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <div className="header-div z-depth-1">
                    <div className="left">
                        <img src="./img/Uno.png" className="logo-sm"/>
                    </div>
                </div>
                <Facebook fb={this.props.fb}/>
            </div>
        );
    }
}

ReactDOM.render(<App fb={FB}/>, document.getElementById("react-app"));