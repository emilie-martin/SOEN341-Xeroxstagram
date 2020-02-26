import React from 'react'

export const CommentComponent = ({props}) => {
    return (
        <div className="comment-div">
                <br/>
                <Link to={`/account/${this.props.comment.account}`}>{this.props.comment.account}</Link>: {this.props.comment.comment}
                <div className="date-created">{new Date(this.props.comment.created).toUTCString()}</div>
                <br/>
        </div>
    )
}
