import React, { useState, useEffect } from 'react'
import { getCommentByPicture } from './CommentAPI'
import { Comment } from './Comment'
import './SCSS/CommentList.scss'

const CommentList = (props) => {

    const [commentList, setCommentList] = useState([])

    useEffect(() => {
        const loadComments = () => {
            getCommentByPicture(props.postId).then((response) => {
                setCommentList(response);
            });
        }
        loadComments();
    }, [props.postId, props.refreshComment]);

    return (
        <div className="comment-list-wrapper">
            <div className="comment-list">
                {
                    commentList.slice().reverse()
                        .map(comment => <Comment key={comment.id} comment={comment}></Comment>)
                }
            </div>
        </div>
    );
}
export default CommentList;