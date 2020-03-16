import '../../config';
import axios from "axios";
import { Link } from "react-router-dom";
import React, { useState, useEffect } from 'react'

import timeElapsedSincePosted from "../../services/TimeService";

import CommentList from "../Comment/CommentList";
import PostImage from "./PostImage";
import { PostComment } from "../Comment/PostComment";

import "./Post.scss";

export default function Post(props) {
    const [Picture, setPicture] = useState(undefined);
    const [refreshComment, setRefreshComment] = useState(false);

    useEffect(() => {
        const loadPicture = () => {
            axios.get(global.config.BACKEND_URL + "/picture/" + props.id)
            .then((response) => {setPicture(response.data);})
            .catch(() => {setPicture(null);})
        }

        loadPicture();

    }, [props.id])

    const onCommentPosted = () => {
        // Whenever a comment is posted, inverse the boolean associated to refreshComment
        // This state will be passed into a commentList in order to make the component rerender
        setRefreshComment(!refreshComment);
    }

    return (
        <div>
            {Picture
                ? <div className="post">
                    <PostImage pictureId={props.id}/>
                    <div className="text-wrapper">
                        <div className="post-description">
                            <div className="account-name">
                                <Link to={`/account/${Picture.account}`}>
                                    {Picture.account}
                                </Link>: {Picture.caption}
                            </div>
                            <div className="date-created">{timeElapsedSincePosted(new Date(Picture.created))}</div>
                        </div>
                        <div className="comments">
                            <CommentList refreshComment={refreshComment} postId={props.id} />
                        </div>
                        <div className="posts-comment">
                            <PostComment postId={props.id} onCommentPosted={onCommentPosted} />
                        </div>
                    </div>
                 </div>
                : Picture && <div className="error"><p>The picture could not be found.</p></div>
            }
        </div>
    )
}
