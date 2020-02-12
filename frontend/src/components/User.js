import React from "react";
import axios from "axios";
import '../config'
import Post from "./Post";

class User extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            User: null,
            Pictures: []
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
        // to do
        axios.get(global.config.BACKEND_URL + "/" + this.props.username + "/pictures").then(
            (response) => {
                this.setState({Pictures: response.data});
            }
        ).catch(
            (error) => {
                // to do: handle error
                console.log(error);
                this.setState({
                    Pictures: null
                });
            }
        )
    }

    render() {
        return (
            <div>
                {
                    this.state.Pictures.map((id) => (
                        <div>
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
