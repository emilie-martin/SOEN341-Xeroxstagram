import React, { useState, useEffect } from 'react'
import { deleteComment, editComment } from './CommentAPI'
import Popup from 'reactjs-popup'
import './SCSS/EditComment.scss'
export const EditComment = (props) => {

    useEffect(() => {
    }, [])

    const updateComment = (event)=>{
        event.preventDefault();
        console.log(textAreaText);
        editComment(props.commentId, textAreaText).then(()=>{
            setTextAreaText("");
            props.reloadComment();
        });
        
    }

    const [textAreaText, setTextAreaText] = useState("");

    const handleTyping = (e) => {
        setTextAreaText(e.target.value);
    }

return(
    <Popup modal trigger={<a href="#" className="editButton"><span>&#8942;</span></a>} closeOnDocumentClick 
    style="background:black;">
        {close => (
            <div className="editPage">
            <button className="editPageButton" style={{color:"red"}} onClick={() => {deleteComment(props.commentId);close();props.reloadComment()}}>Delete</button>
            <br/>
            <Popup trigger={<a href="#">Edit</a>} closeOnDocumentClick>
                <form onSubmit={(e)=>{updateComment(e); close()}}>
                    <textarea value = {textAreaText} onChange={handleTyping} autoComplete="off"></textarea>
                    <button>Update</button>
                </form>
            </Popup>
            <br/>
            <button className="editPageButton" onClick={()=>close()}>Cancel</button>
            </div>
        )      
        }
    </Popup>
)
    

}
