import React from 'react'

import { useState } from 'react'

export const Comment = ({comment}) => {
    const [nbLikes, setnbLikes] = useState(comment.nbLikes);
    
    return (
        <div>
            <h6>User: {comment.account}</h6>
            <p>Comment: {comment.comment}</p>
            {/*Handle if already liked later*/}
            {/*<button onClick={() => setnbLikes((currentNbLikes) => likeComment(comment.id, currentNbLikes))}></button>*/}
            <p>Number of likes: {nbLikes}</p>
            <p>Date created:{comment.created}</p>
        </div>
    )
}


