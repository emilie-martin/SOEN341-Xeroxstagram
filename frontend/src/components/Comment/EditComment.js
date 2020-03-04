import React, { useState } from 'react'
import { deleteComment, editComment } from './CommentAPI'
import Popup from 'reactjs-popup'
import './SCSS/EditComment.scss'
export const EditComment = (props) => {
    const [textAreaText, setTextAreaText] = useState(props.comment.comment);

    const updateComment = (event) => {
        event.preventDefault();
        editComment(props.commentId, textAreaText).then(() => {
            setTextAreaText("");
            props.reloadComment();
        });
    }

    const removeComment = (event) => {
        event.preventDefault();
        deleteComment(props.commentId).then(() => {
            props.reloadComment();
        });
    }

    const handleTyping = (e) => {
        setTextAreaText(e.target.value);
    }

    return (
        <div>
            <Popup modal className="edit" trigger={<a href="#" className="edit-button"><span>&#8942;</span></a>} closeOnDocumentClick
                style="background:black;">
                {close => (
                    <div className="edit-page">
                        <button className="edit-page-button" style={{ color: "red" }} onClick={(e) => { removeComment(e); close(); }}>Delete</button>
                        <br />
                        <Popup className="edit-menu" trigger={<button className="edit-page-button">Edit</button>} closeOnDocumentClick>
                            <form onSubmit={(e) => { updateComment(e); close() }}>
                                <textarea className="edit-text-area" value={textAreaText} onChange={handleTyping} autoComplete="off"></textarea>
                                <br />
                                <button className="update-comment-btn">Update</button>
                            </form>
                        </Popup>
                        <br />
                        <button className="edit-page-button" onClick={() => close()}>Cancel</button>
                    </div>
                )
                }
            </Popup>
        </div>
    )
}
