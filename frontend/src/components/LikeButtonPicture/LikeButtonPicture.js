import React, { useState, useEffect } from 'react'
import axios from 'axios'

import "./LikeButtonPicture.scss"

export default function LikeButtonPicture(props) {
    const [likeStatus, setLikeStatus] = useState(false);
    const [likeCount, setLikeCount] = useState(props.likeCount);

    useEffect(() => {
        const getLikeStatusPicture = () => {
            axios.get(global.config.BACKEND_URL + "/picture/likeStatus/" + props.postId)
                .then(
                    (response) => { setLikeStatus(response.data); }
                )
                .catch(
                    () => { alert("unexpected error! 🥔") }
                )
        }
        getLikeStatusPicture();
    }, [likeStatus, likeCount, props.postId]) //when likeStatus change, re-render component (and call useEffect)

    const likePicture = (event) => {
        event.preventDefault();
        likeStatus ?
            axios.post(global.config.BACKEND_URL + "/picture/likeRemoval/" + props.postId).then(
                (response) => { setLikeStatus(false); setLikeCount(response.data) }
            )
                .catch(
                    (error) => { alert(error.response.data.message) }
                )
            : axios.post(global.config.BACKEND_URL + "/picture/like/" + props.postId).then(
                (response) => { setLikeStatus(true); setLikeCount(response.data) }
            )
                .catch(
                    (error) => { alert(error.response.data.message) }
                )
    }

    return (
        <div className="like-component-picture">
            <div className="like-button" onClick={(event) => likePicture(event)}>
                {likeStatus ? "🌕" : "🌑"}
            </div>
            <div>
                {likeCount}
            </div>
        </div>
    )
}
