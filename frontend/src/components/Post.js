import React from "react";
import axios from "axios";
import "./Post.scss";
import '../config'

class Post extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            Picture: null
        }
    }

    componentDidMount() {
        this.loadPicture();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(prevProps.id !== this.props.id) {
            this.loadPicture();
        }
    }

    loadPicture() {
        axios.get(global.config.BACKEND_URL + "/picture/" + this.props.id).then(
            (response) => {
                this.setState({Picture: response.data});
            }
        ).catch(
            (error) => {
                // to do: handle error
                console.log(error);
                this.setState({
                    Picture: null
                });
            }
        )
    }

    render() {
        return (
            <div>
                {this.state.Picture &&
                    <div className="post">
                        <div className="image-wrapper">
                            <img src={`${global.config.BACKEND_URL}/picture/${this.props.id}.jpg`}
                                 alt="insert post here"/>
                        </div>
                        <div className="text-wrapper">
                            {/* a Description component can be created to facilitate the creation of Post components */}
                            <div className="post-description">{this.state.Picture.account}: {this.state.Picture.caption}</div>
                            <div className="post-comments">
                                {/* similarly, a Comment component should be later created and implemented here */}
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                                <p>comment</p>
                            </div>
                        </div>
                    </div>
                }
            </div>
        );
    }
}

export default Post;
