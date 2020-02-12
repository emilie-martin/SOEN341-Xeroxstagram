import React from "react";

import "./Post.scss";

class Post extends React.Component {
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
                        {/* similarly, a Comment component should be later created and implemented here */}
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                        <p>comment</p>
                    </div>
                </div>
            </div>
        );
    }
}

export default Post;
