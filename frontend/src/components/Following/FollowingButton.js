import '../../config';
import axios from "axios";
import LocalStorageService from '../../services/LocalStorageService';
import React, { useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';

import './FollowingButton.scss'

export default function FollowingButton(props) {
    const [isFollowing, setIsFollowing] = useState();
    const [errorMsg, setErrorMsg] = useState("");
    const history = useHistory();

    useEffect(() => {
        const isUserFollowing = () => {
            console.log(props.username);
            axios.get(global.config.BACKEND_URL + "/account/following/" + props.username,
                { headers: { Authorization: `Bearer ${LocalStorageService.getAccessToken()}` } })
                .then(
                    (response) => {
                        setIsFollowing(Boolean(response.data));
                    })
                .catch(
                    () => {
                        alert("Unexpected error, redirecting to home");
                        history.push("/");
                    })
        }
        isUserFollowing();
    }, [props.currentUser, props.username, history])

    const follow = (event) => {
        event.preventDefault();
        axios.post(global.config.BACKEND_URL + "/account/following/newFollower/" + props.username)
            .then(() => {
                setIsFollowing(true);
                props.refreshProfile();
            })
            .catch(
                (error) => {
                    (error.response && error.response.data && error.response.data.message)
                        ? setErrorMsg(error.response.data.message)
                        : setErrorMsg("An unknown error occured");
                    alert(errorMsg.toString());
                })
    };

    const unfollow = (event) => {
        event.preventDefault();
        axios.delete(global.config.BACKEND_URL + "/account/following/followerRemoval/" + props.username)
            .then(() => {
                setIsFollowing(false);
                props.refreshProfile();
            })
            .catch(
                (error) => {
                    error.response ? setErrorMsg(error.response.data.message) : setErrorMsg("An unknown error occured");
                    alert(errorMsg.toString());
                })
    };

    return (
        <div className="follow-button-component">
            <form onSubmit={isFollowing ? unfollow : follow}>
                <button className={props.class} type="submit">{isFollowing ? "Unfollow" : "Follow"}</button>
            </form>
        </div>
    );
};
