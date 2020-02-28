import React, { useState }  from 'react'
import "./CommentAPI";
import { postComment } from "./CommentAPI";
import "./SCSS/PostComment.scss";

export const PostComment = (props) => {

    const [textAreaText, setTextAreaText] = useState("");

    const handleTyping = (e) => {
        setTextAreaText(e.target.value);
    }

    //Post a comment
    const comment = (e) => {
        e.preventDefault();
        postComment(textAreaText, props.postId).then(
            () => {
                props.onCommentPosted();
                setTextAreaText("");
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

    return (
        <div>
            <form onSubmit={comment}>
                <div className="comment-form-div">
                    <textarea value ={textAreaText} name="comment" className="comment-input" autoComplete="off" onChange={handleTyping}/><button className="btn">Post</button>
                </div>
            </form>
        </div>
    )
}
