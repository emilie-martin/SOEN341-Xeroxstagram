import React from 'react'
import { Comment } from './Comment'
import { useState, useEffect } from 'react'
import CommentAPI, { getCommentByPicture } from './CommentAPI';

const CommentList = () => {
    const [commentList, setcommentList] = useState([]);
    useEffect(() => {
        Promise.resolve(getCommentByPicture(1)).then((response) => {
            setcommentList(response);
        })
    })
    return (

        <div>
            <ul>
                {
                    commentList.map(comment => <Comment comment={comment}></Comment>)
                }
            </ul>
            {/* {commentList} */}
        </div>
    )
}


export default CommentList