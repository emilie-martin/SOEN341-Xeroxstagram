import React, { useState, useEffect } from 'react'
import axios from "axios";
import { Link } from "react-router-dom";
import { PostComment } from "./Comment/PostComment";
import "./Post.scss";
import '../config';
import CommentList from "./Comment/CommentList";
const Post = (props) => {

    const [Picture, setPicture] = useState(undefined);
    const [refreshComment, setRefreshComment] = useState(false);

    useEffect(() => {
        const loadPicture = () => {
            axios.get(global.config.BACKEND_URL + "/picture/" + props.id).then(
                (response) => {
                    setPicture(response.data);
                }
            ).catch(
                () => {
                    setPicture(null);
                }
            )
        }
        loadPicture();
    }, [props.id])

    const onCommentPosted = () => {
        //Whenver a comment is posted, inverse the boolean associated to refreshComment
        //This state will be passed into a commentList in order to make the component rerender
        setRefreshComment(!refreshComment);
    }

    return (
        <div>
            {Picture ?
                <div className="post">
                    <div className="image-wrapper">
                        <img src={`${global.config.BACKEND_URL}/picture/${props.id}.jpg`}
                            alt={`${props.id}`} />
                    </div>
                    <div className="text-wrapper">
                        {/* a Description component can be created to facilitate the creation of Post components */}
                        <div className="post-description">
                            <div className="account-name">
                                <Link to={`/account/${Picture.account}`}>
                                    {Picture.account}
                                </Link>: {Picture.caption}
                            </div>

                        </div>
                        <div className="comments">
                            <CommentList refreshComment={refreshComment} postId={props.id} />
                        </div>
                        <div className="posts-Comment">
                            <PostComment postId={props.id} onCommentPosted={onCommentPosted} />
                        </div>
                    </div>
                </div>
                :
                Picture === undefined ? null : <div className="error">The picture could not be found.</div>
            }
        </div>
    )
}
export default Post;