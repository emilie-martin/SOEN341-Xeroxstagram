import React, { useState, useEffect } from 'react'
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
            <Popup modal className="edit" trigger={<a href="#" className="editButton"><span>&#8942;</span></a>} closeOnDocumentClick
                style="background:black;">
                {close => (
                    <div className="editPage">
                        <button className="editPageButton" style={{ color: "red" }} onClick={(e) => { removeComment(e); close(); }}>Delete</button>
                        <br />
                        <Popup className="editMenu" trigger={<button className="editPageButton">Edit</button>} closeOnDocumentClick>
                            <form onSubmit={(e) => { updateComment(e); close() }}>
                                <textarea className="editTextArea" value={textAreaText} onChange={handleTyping} autoComplete="off"></textarea>
                                <br />
                                <button className="updateCommentBtn">Update</button>
                            </form>
                        </Popup>
                        <br />
                        <button className="editPageButton" onClick={() => close()}>Cancel</button>
                    </div>
                )
                }
            </Popup>
        </div>
    )
}
