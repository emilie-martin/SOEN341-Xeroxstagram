import React from 'react'
import { useState, useEffect } from 'react'
import { getCommentByPicture } from './CommentAPI'
import {Comment} from './Comment'
import './SCSS/CommentList.scss'

const CommentList = (props) => {

    const [commentList, setcommentList] = useState([])

    useEffect(() => {
        loadComments();
    }, [props.postId, props.refreshComment]);

    const loadComments = () => {
        console.log(props);
        getCommentByPicture(props.postId).then((response) => {
            console.log(response);
            setcommentList(response);
        });
    }

    return (
        <div>
            <div className="comment-list-wrapper">
                <div className="comment-list">
                    {
                        commentList.slice().reverse()
                            .map(comment => <Comment key={comment.id} comment={comment}></Comment>)
                    }
                </div>
            </div>
        </div>
    );
} 
export default CommentList