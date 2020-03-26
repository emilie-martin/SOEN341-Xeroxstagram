import axios from 'axios'
import React, { useState, useEffect } from 'react'

import "./LikeButtonComment.scss"

export default function LikeButtonComment(props) {
    const [likeStatus, setLikeStatus] = useState(false);
    const [likeCount, setLikeCount] = useState(props.likeCount);

    useEffect(() => {
        const getLikeStatusComment = () => {
            axios.get(global.config.BACKEND_URL + "/comment/likeStatus/" + props.postId)
                .then(
                    (response) => { setLikeStatus(response.data); }
                )
                .catch(
                    () => { alert("unexpected error! 🥔") }
                )
        }
        getLikeStatusComment();
    }, [likeStatus, likeCount, props.postId])

    const likeComment = (event) => {
        event.preventDefault();
        likeStatus
            ? axios.post(global.config.BACKEND_URL + "/comment/likeRemoval/" + props.postId)
                .then(
                    (response) => { setLikeStatus(false); setLikeCount(response.data) }
                )
                .catch(
                    (error) => { alert(error.response.data.message) }
                )
            : axios.post(global.config.BACKEND_URL + "/comment/like/" + props.postId)
                .then(
                    (response) => { setLikeStatus(true); setLikeCount(response.data) }
                )
                .catch(
                    (error) => { alert(error.response.data.message) }
                )
    }
    
    return (
        <div className="like-component-comment">
            <div className="like-button" onClick={(event) => likeComment(event)}>
                {likeStatus ? "🌕" : "🌑"}
            </div>
            <div>
                {likeCount}
            </div>
        </div>
    )
}