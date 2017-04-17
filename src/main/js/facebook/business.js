const React = require('react');

import Uno from './uno';

export default class BusinessForm extends React.Component {

    constructor(props) {
        super(props);
        this.page = props.page;
        this.fbUserId = props.fbUserId;
        this.saveBusiness = props.saveBusiness;
        this.state = {
            categories: [],
            name: this.page.name,
            category: "0",
            description: '',
            address: '',
            businessSaved: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        const target = e.target;
        const value = target.type == 'checkbox' ? target.checked : target.value;
        const name = target.name;

        this.setState({
            [name]: value
        });
    }

    handleSubmit(e) {
        e.preventDefault();
        e.stopPropagation();

        Uno.api("/businesses/", 'POST', {
            name: this.state.name,
            category: this.state.category,
            description: this.state.description,
            address: this.state.address,
            fb_page_id: this.page.id,
            fb_user_id: this.fbUserId
        }, function (response) {
            console.log(response, "Create business");
            if (response.success) {
                this.saveBusiness(true);
                this.setState({
                    businessSaved: true
                })
            }
        }.bind(this))
    }

    componentDidMount() {
        Uno.api("/businesses/categories", function (response) {
            console.log(response, "Categories");
            if (response.success) {
                this.setState({
                    categories: response.data
                });
                $('select').material_select();
            }
        }.bind(this));

        Uno.api(`/businesses?fb_page=${this.page.id}`, function (response) {
            console.log(response, "Business for Page");
            if (response.success && response.data) {
                this.setState({
                    businessSaved: true
                })
            }
        }.bind(this));
    }

    connectAgent(agent) {

    }

    render() {
        if (this.state.businessSaved) {
            return (
                <ConnectAgent connectAgent={this.connectAgent.bind(this)}/>
            );
        } else {
            return (
                <div className="container">
                    <div className="row center">
                        <h5 className="light">Tell us about your business</h5>
                    </div>
                    <div className="row">
                        <form className="col s12" onSubmit={this.handleSubmit}>
                            <div className="row">
                                <div className="input-field col s6">
                                    <input placeholder="Business Name" name="name" type="text"
                                           onChange={this.handleChange}
                                           className="validate" value={this.state.name}/>
                                    <label htmlFor="name" className="active">Business Name</label>
                                </div>
                                <div className="input-field col s6">
                                    <label htmlFor="category" className="active">Category</label>
                                    <select name="category" value={this.state.category}
                                            onChange={this.handleChange} className="browser-default">
                                        <option value="0" disabled>Business Type</option>
                                        {
                                            this.state.categories.map((cat) =>
                                                <option value={cat.name} key={cat.id}>{cat.name}</option>)
                                        }
                                    </select>
                                </div>
                            </div>
                            <div className="row">
                                <div className="input-field col s12">
                                            <textarea name="description" className="materialize-textarea"
                                                      onChange={this.handleChange}
                                                      placeholder="Tell us about what your business is about"
                                                      value={this.state.description}></textarea>
                                    <label htmlFor="description" className="active">Description</label>
                                </div>
                            </div>
                            <div className="row">
                                <div className="input-field col s12">
                                <textarea name="address" className="materialize-textarea" onChange={this.handleChange}
                                          placeholder="" value={this.state.address}></textarea>
                                    <label htmlFor="address">Address</label>
                                </div>
                            </div>
                            <div className="row">
                                <div className="col s4 offset-s4">
                                    <button className="btn blue darken-4 col s12" type="submit"
                                            name="save_business">
                                        Save
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            );
        }
    }
}

class ConnectAgent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            agents: []
        }
    }

    componentDidMount() {
        Uno.api("/ai/agents", function (response) {
            if (response.success) {
                this.setState({
                    agents: response.data
                })
            }
        }.bind(this))
    }

    connect(e) {
        console.log(e.target, "connect agent click target");
        console.log(e.target.name, "connect agent click target name");
        console.log(e.target.props.agent, "connect agent click target.props.agent");
    }

    render() {
        return (
            <div className="container">
                <ul className="collection">
                    {this.state.agents.map((agent) =>
                        <li className="collection-item" key={agent.name}>
                            <h5 className="thin">{agent.name}</h5>

                            <p>{agent.description}</p>
                            <a className="btn blue darken-4" onClick={this.connect.bind(this)} name={agent.id}
                               agent={agent}>Connect</a>
                        </li>)}
                </ul>
            </div>
        );
    }
}