import Axios from 'axios';
import '../../config';

export const getCommentByPicture =  (pictureId) => {
    return Axios.get(global.config.BACKEND_URL + `/comment/commentByPicture/${pictureId}`).then(response => {
        console.log(response.data);
        return response.data;
    }).catch((error) => {
        //handle error later
        //error possible is that picture is non existent
        //return Promise.reject(error) this makes the return Promise a reject
        console.log(error);
    });
};

export const postComment = async (comment, pictureId) => {
    return Axios.post(global.config.BACKEND_URL + `/comment/newComment/${pictureId}`, {
        comment
    });
};

export const deleteComment = async (commentId) => {
    console.log(commentId);
    return Axios.delete(global.config.BACKEND_URL + `/comment/commentRemoval/${commentId}`).then(response =>{
        return response.status;
    }).catch((error) =>{
        console.log("in error");
        return error;
    });
}

export const editComment =  (commentId, comment) => {
    return Axios.put(global.config.BACKEND_URL + `/comment/commentUpdate/${commentId}`,{comment}).then(response =>{
        return response.data;
    }).catch(error => {
        console.log(error.response);
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