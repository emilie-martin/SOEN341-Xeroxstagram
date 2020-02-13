import React from "react";
import axios from "axios";
import '../config';

class PostPicture extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            errorMsg: ""
        }
    }
    submit(event) {
        event.preventDefault();
        const data = new FormData();
        data.append('picture', event.target.picture.files[0]);
        data.append('caption', event.target.caption.value);
        axios.post(global.config.BACKEND_URL + "/picture", data)
            .then(
                (response) => {
                    this.props.history.push(`/post/${response.data.id}`);
                },
                (e) => {
                    this.setState({errorMsg: e.response.data.message});
                }
            )
    }

    render() {
        return (
            <div className="postPicture">
                <form onSubmit={this.submit.bind(this)}>
                    <label>File</label>
                    <input name="picture" type="file"/>
                    <br/>
                    <label>Caption</label>
                    <input name="caption"/>
                    <br/>
                    {this.state.errorMsg && <div className="error">Error: {this.state.errorMsg}</div>}
                    <button type="submit">
                        Post
                    </button>
                </form>
            </div>
        );
    }
}

export default PostPicture;
