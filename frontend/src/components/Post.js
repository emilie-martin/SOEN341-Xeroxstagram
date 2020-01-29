import React from "react";

import "./Post.scss";

export default function Post() {
  return(
    <div class="post">
      <div class="image-wrapper">
        <img src="ig-frontend\src\images\image-icon.png" alt="insert post here"/>
      </div>
      <div class="text-wrapper">
        {/* a Description component can be created to facilitate the creation of Post components */}
        <div class="post-description">Insert description, timestamp, and poster info here</div>
        <div class="post-comments">
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
