import React, { useState, useEffect } from 'react'
import { Link } from "react-router-dom"
import './SCSS/Comment.scss'
import { timeElapseSincePosted } from '../../services/TimeService'
export const Comment = (props) => {
    const [timePosted, setTimePosted] = useState("");

    useEffect(() => {
        setTimePosted(timeElapseSincePosted(new Date(props.comment.created)));
    }, []);

    return (
        <div className="comment-div">
            <br />
            <Link to={`/account/${props.comment.account}`}>{props.comment.account} </Link> {props.comment.comment}
            <div className="date-created">{timePosted} ago</div>
            <br />
        </div>
    );
}