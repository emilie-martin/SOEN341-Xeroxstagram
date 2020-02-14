import React from "react";
import "./CommentAPI";
import { postComment } from "./CommentAPI";
import "./PostComment.scss";

class PostComment extends React.Component {

    comment(e) {
        e.preventDefault();
        postComment(e.target.comment.value, this.props.postId).then(
            () => {
                this.props.onCommentPosted();
            }
        ).catch((e) => {
            // todo: proper error feedback
            if (e.response) {
                if (e.response.status === 401) {
                    alert("You must be logged in to comment.")
                } else if (e.response.data.errors) {
                    alert("Your comment cannot be blank.");
                } else {
                    alert(e.response.data.message);
                }
            } else {
                alert("An unknown error occurred.");
            }
        });
    }

    render() {
        return (
            <form onSubmit={this.comment.bind(this)}>
                <div className="comment-form-div">
                    <input name="comment" className="comment-input"/><button>Comment</button>
                </div>
            </form>
        );
    }
}

export default PostComment;