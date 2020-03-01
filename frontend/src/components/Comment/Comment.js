import React, { useState, useEffect } from 'react'
import { Link } from "react-router-dom"
import './SCSS/Comment.scss'
import { timeElapseSincePosted } from '../../services/TimeService'
import Popup from "reactjs-popup"
import { EditComment } from './EditComment'
export const Comment = (props) => {
    const [timePosted, setTimePosted] = useState("");
    const [currentComment, setCurrentComment] = useState(props.comment.comment);
    useEffect(() => {
        setTimePosted(timeElapseSincePosted(new Date(props.comment.created)));
    }, [timePosted,currentComment]);
    
    const refreshComment = (comment)=>{
        setCurrentComment(comment);
    }
    return (
        <div className="comment-div">
            <br />
            <Link to={`/account/${props.comment.account}`}>{props.comment.account} </Link> 
            <EditComment {...props} commentId={props.comment.id} ></EditComment>
            <div className="commentContent">
                {currentComment}
            </div>
            <div className="date-created">{timePosted} ago</div>
            <br />
        </div>
    );
}