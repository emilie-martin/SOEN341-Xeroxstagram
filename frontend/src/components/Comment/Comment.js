import React from 'react'
import "./Comment.scss";
import {Link} from "react-router-dom";

class Comment extends React.Component {
    //todo: add like on comments
    render() {
        return (
            <div className="comment-div">
                <br/>
                <Link to={`/account/${this.props.comment.account}`}>{this.props.comment.account}</Link>: {this.props.comment.comment}
                <div className="date-created">{new Date(this.props.comment.created).toUTCString()}</div>
                <br/>
            </div>
        )
    }
}

export default Comment;


