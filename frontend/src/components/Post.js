import React from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import "./Post.scss";
import CommentList from "./Comment/CommentList";
import '../config';

class Post extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            Picture: undefined
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
            () => {
                this.setState({
                    Picture: null
                });
            }
        )
    }

    render() {
        return (
            <div>
                {this.state.Picture ?
                    <div className="post">
                        <div className="image-wrapper">
                            <img src={`${global.config.BACKEND_URL}/picture/${this.props.id}.jpg`}
                                 alt="insert post here"/>
                        </div>
                        <div className="text-wrapper">
                            {/* a Description component can be created to facilitate the creation of Post components */}
                            <div className="post-description">
                                <Link to={`/account/${this.state.Picture.account}`}>
                                    {this.state.Picture.account}
                                </Link>: {this.state.Picture.caption}
                            </div>
                            <div className="post-comments">
                                {/* similarly, a Comment component should be later created and implemented here */}
                                <CommentList/>
                            </div>
                        </div>
                    </div>
                    :
                    this.state.Picture === undefined ? null : <div className="error">The picture could not be found.</div>
                }
            </div>
        );
    }
}

export default Post;
