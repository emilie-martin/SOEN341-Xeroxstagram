import React, { useState, useEffect} from 'react'
import { getCommentByPicture } from './CommentAPI'
import { Comment } from './Comment'
import './SCSS/CommentList.scss'

const CommentList = (props) => {

    const [commentList, setCommentList] = useState([])
    const [refreshCommentList, setRefreshCommentList] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const loadComments = () => {
            getCommentByPicture(props.postId).then((response) => {
                setCommentList(response);
            });
        }
        loadComments();
    }, [props.postId, props.refreshComment, refreshCommentList]);

    const reloadComment = () => {
        setLoading(true);
        setRefreshCommentList(!refreshCommentList);
    }

    return (
        <div className="comment-list-wrapper">
            <div className="comment-list">
                {loading ? 'Loading Comments' : commentList.slice().reverse()
                    .map(comment => <Comment key={comment.id} comment={comment} reloadComment={reloadComment}></Comment>)
                }
            </div>
        </div>
    );
}
export default CommentList;