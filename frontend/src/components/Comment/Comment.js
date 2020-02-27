import React from 'react'
import { Link } from "react-router-dom"
import { useEffect } from 'react'
import './SCSS/Comment.scss'
export const Comment = (props) => {
    return (
        <div className="comment-div">
            <br />
            <Link to={`/account/${props.comment.account}`}>{props.comment.account} </Link> {props.comment.comment}
            <div className="date-created">{new Date(props.comment.created).toDateString()}</div>
            <br />
        </div>
    )
}