import React from 'react'
import Comment from './Comment'
import { getCommentByPicture } from './CommentAPI';

class CommentList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            commentList: []
        }
    }
    componentDidMount() {
        this.loadComments();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if(prevProps.postId !== this.props.postId) {
            this.loadPicture();
        }
    }

    loadComments() {
        getCommentByPicture(this.props.postId).then((response) => {
            this.setState({commentList: response});
        });
    }

    render() {
        return (
            <div className="comment-list-wrapper">
                <div className="comment-list">
                    {
                        this.state.commentList.slice().reverse()
                            .map(comment => <Comment key={comment.id} comment={comment}></Comment>)
                    }
                </div>
            </div>
        );
    }
}


export default CommentList;