import React, { useState, useEffect } from 'react'
import { getCommentByPicture } from './CommentAPI'
import { Comment } from './Comment'
import './SCSS/CommentList.scss'
import Axios from 'axios'

const CommentList = (props) => {

    const [commentList, setCommentList] = useState([])
    const [refreshCommentList, setRefreshCommentList] = useState(false);

    useEffect(() => {
        loadComments();
    }, [props.postId, props.refreshComment, refreshCommentList]);

    const loadComments = () => {
        const fetchComments = async () => { 
            const response = await Axios.get(global.config.BACKEND_URL + `/comment/commentByPicture/${props.postId}`); 
            setCommentList(response.data);
        };
        fetchComments();
    }

    const reloadComment = () => {
        getCommentByPicture(props.postId).then(response => {
            setCommentList(response);
        });
}
    return (
        <div className="comment-list-wrapper">
            <div className="comment-list">
                {
                    commentList.slice().reverse()
                        .map(comment => <Comment key={comment.id} comment={comment} reloadComment={reloadComment}></Comment>)
                }
            </div>
        </div>
    );
}
export default CommentList;