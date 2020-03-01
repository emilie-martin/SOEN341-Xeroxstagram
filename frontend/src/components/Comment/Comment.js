import React, { useState, useEffect } from 'react'
import { Link } from "react-router-dom"
import './SCSS/Comment.scss'
import { timeElapseSincePosted } from '../../services/TimeService'
import Popup from "reactjs-popup"
import { EditComment } from './EditComment'
export const Comment = (props) => {
    const [timePosted, setTimePosted] = useState("");

    useEffect(() => {
        setTimePosted(timeElapseSincePosted(new Date(props.comment.created)));
    }, []);
    
    return (
        <div className="comment-div">
            <br />
            <Link to={`/account/${props.comment.account}`}>{props.comment.account} </Link> 
            {Boolean(props.comment.editable) ? <EditComment {...props} commentId={props.comment.id} ></EditComment>: ''}
            <div className="commentContent">
                {props.comment.comment}
            </div>
            <div className="date-created">{timePosted} ago</div>
            <br />
        </div>
    );
}