import { Link } from "react-router-dom"
import React, { useState, useEffect } from "react"

import EditComment from "./EditComment"
import LikeButtonComment from "../LikeButtonComment/LikeButtonComment"
import timeElapsedSincePosted from "../../services/TimeService"

import "./SCSS/Comment.scss"

export default function Comment(props) {
	const [timePosted, setTimePosted] = useState("");

	useEffect(() => {
			setTimePosted(timeElapsedSincePosted(new Date(props.comment.created)));
	}, [props]);

	return (
		<div className="comment-component">
			<div className="comment-div">
				<div className="comment-content">
					<Link to={`/account/${props.comment.account}`}>{props.comment.account}</Link>
					{props.comment.comment}
				</div>
				<div className="comment-edit">
					{ Boolean(props.comment.editable) && <EditComment {...props} commentId={props.comment.id} ></EditComment> }
				</div>
			</div>
			<div className="like-div">
				<div className="date-created">{timePosted}</div>
				<LikeButtonComment postId={props.comment.id} likeCount={props.comment.likeCount}></LikeButtonComment>
			</div>
		</div>
	);
}