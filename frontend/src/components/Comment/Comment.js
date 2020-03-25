import { Link } from "react-router-dom"
import React, { useState, useEffect } from 'react'

import EditComment from './EditComment'
import timeElapsedSincePosted from '../../services/TimeService'

import './SCSS/Comment.scss'

export default function Comment(props) {
    const [timePosted, setTimePosted] = useState("");

    useEffect(() => {
        setTimePosted(timeElapsedSincePosted(new Date(props.comment.created)));
    }, [props]);

    return (
        <div className="comment-div">
            <Link to={`/account/${props.comment.account}`}>{props.comment.account} </Link> 
            {Boolean(props.comment.editable) ? <EditComment {...props} commentId={props.comment.id} ></EditComment>: ''}
            <div className="comment-content">
                {props.comment.comment}
            </div>
            <div className="date-created">{timePosted}</div>
        </div>
    );
}