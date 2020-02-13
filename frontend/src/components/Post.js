import React from "react";

import "./Post.scss";
import CommentList from "./Comment/CommentList";

class Post extends React.Component {
    // to access passed id: this.props.match.params.id
    render() {
        return (
            <div className="post">
                <div className="image-wrapper">
                    <img src="to add" alt="insert post here"/>
                </div>
                <div className="text-wrapper">
                    {/* a Description component can be created to facilitate the creation of Post components */}
                    <div className="post-description">Insert description, timestamp, and poster info here</div>
                    <div className="post-comments">
                        <CommentList></CommentList>
                    </div>
                </div>
            </div>
        );
    }
}

export default Post;
