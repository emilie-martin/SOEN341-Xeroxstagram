import Axios from 'axios';
import '../../config';

export const getCommentByPicture = (pictureId) => {
    return Axios.get(global.config.BACKEND_URL + `/comment/commentByPicture/${pictureId}`).then(response => {
        return response.data;
    }).catch((error) => {
        alert(error.response.data.message);
    });
};

export const postComment = (comment, pictureId) => {
    return Axios.post(global.config.BACKEND_URL + `/comment/newComment/${pictureId}`, {
        comment
    });
};

export const deleteComment = (commentId) => {
    return Axios.delete(global.config.BACKEND_URL + `/comment/commentRemoval/${commentId}`).then(response => {
        return response.status;
    }).catch((error) => {
        alert(error.response.data.message);
        return error;
    });
}

export const editComment = (commentId, comment) => {
    return Axios.put(global.config.BACKEND_URL + `/comment/commentUpdate/${commentId}`, { comment }).then(response => {
        return response.data;
    }).catch(error => {
        alert(error.response.data.message);
    });
}

export const likeComment = (commentId, nbOfLikes) => {
    return Axios.post(global.config.BACKEND_URL + `/comment/like/${commentId}`).then(response => {
        return response.data;
    }).catch((error) => {
        return nbOfLikes;
    });
};

export const unlikeComment = (commentId) => {
    return Axios.post(global.config.BACKEND_URL + `/comment/likeRemoval/${commentId}`).then(response => {
        return response.data;
    }).catch((error) => {
        //handle error later
        console.log(error);
    });
};