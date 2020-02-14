import React from "react";
import axios from "axios";
import Post from "./Post";
import '../config';

class User extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            Pictures: [],
            errorMsg: ""
        }
    }

    componentDidMount() {
        this.loadUser();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(prevProps.username !== this.props.username) {
            this.loadUser();
        }
    }

    loadUser() {
        axios.get(global.config.BACKEND_URL + "/" + this.props.username + "/pictures").then(
            (response) => {
                this.setState({
                    Pictures: response.data,
                    errorMsg: ""
                });
            }
        ).catch(
            (error) => {
                this.setState({
                    Pictures: []
                });
                if(error.response && error.response.data && error.response.data.message) {
                    this.setState({
                        errorMsg: error.response.data.message
                    });
                } else {
                    this.setState({
                        errorMsg: "An unknown error occurred."
                    });
                }
            }
        )
    }

    render() {
        return (
            <div>
                {this.state.errorMsg && <div className="error">Error: {this.state.errorMsg}</div>}
                {
                    this.state.Pictures.map((id) => (
                        <div key={id}>
                            <Post id={id}/>
                            <br/>
                        </div>
                    ))
                }
            </div>
        );
    }
}

export default User;
