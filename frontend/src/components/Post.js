import React from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { PostComment } from "./Comment/PostComment";
import "./Post.scss";
import '../config';
import CommentList from "./Comment/CommentList";

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
        if (prevProps.id !== this.props.id) {
            this.loadPicture();
        }
    }

    loadPicture() {
        axios.get(global.config.BACKEND_URL + "/picture/" + this.props.id).then(
            (response) => {
                this.setState({ Picture: response.data });
            }
        ).catch(
            () => {
                this.setState({
                    Picture: null
                });
            }
        )
    }

    onCommentPosted() {
        //Whenver a comment is posted, inverse the boolean associated to refreshComment
        //This state will be passed into a commentList in order to make the component rerender
        this.setState({ refreshComment: !this.state.refreshComment })
    }

    render() {
        return (
            <div>
                {this.state.Picture ?
                    <div className="post">
                        <div className="image-wrapper">
                            <img src={`${global.config.BACKEND_URL}/picture/${this.props.id}.jpg`}
                                alt={`${this.props.id}`} />
                        </div>
                        <div className="text-wrapper">
                            {/* a Description component can be created to facilitate the creation of Post components */}
                            <div className="post-description">
                                <Link to={`/account/${this.state.Picture.account}`}>
                                    {this.state.Picture.account}
                                </Link>: {this.state.Picture.caption}
                            </div>
                            <div className="comments">
                                <CommentList refreshComment={this.state.refreshComment} postId={this.props.id} />
                            </div>
                            <div className="postsComment">
                                <PostComment postId={this.props.id} onCommentPosted={this.onCommentPosted.bind(this)} />
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
