import Axios from 'axios';
import '../../config';

export const getCommentByPicture = async (pictureId) => {
    return Axios.get(global.config.BACKEND_URL + `/comment/commentByPicture/${pictureId}`).then(response => {
        return response.data;
    }).catch((error) => {
        //handle error later
        console.log(error);
    });
};

export const postComment = async(comment, pictureId) => {
    return Axios.post(global.config.BACKEND_URL + `/comment/newComment/${pictureId}`, {
        comment
    });
};

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