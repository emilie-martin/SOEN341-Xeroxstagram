import React from 'react'
import { useState, useEffect, useImperativeHandle } from 'react'
import { getCommentByPicture } from './CommentAPI'
import CommentComponent from './Comment'

export const CommentListComponent = (props) => {

    const [commentList, setcommentList] = useState([])
    useEffect(() => {
        loadComments();
    }, [props.postId, props.refreshComment]);

    const loadComments = () => {
        getCommentByPicture(props.postId).then((response) => {
            setcommentList(response);
        });
    }

    return (
        <div>
            <div className="comment-list-wrapper">
                <div className="comment-list">
                    {
                        commentList.slice().reverse()
                            .map(comment => <CommentComponent key={comment.id} comment={comment}></CommentComponent>)
                    }
                </div>
            </div>
        </div>
    );
} 
