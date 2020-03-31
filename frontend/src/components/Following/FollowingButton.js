import '../../config';
import axios from "axios";
import LocalStorageService from '../../services/LocalStorageService';
import React, { useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';

import './FollowingButton.scss'

export default function FollowingButton(props) {
    const [isFollowing, setIsFollowing] = useState();
    const history = useHistory();
    let errorMsg = "";

    useEffect(() => {
        const isUserFollowing = () => {
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
                    if (error.response.data.error === "unauthorized") {
                        errorMsg = "Must be logged in to follow";
                    } else {
                        errorMsg = "Unexpected error";
                    }
                    alert(errorMsg);
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
                    if (error.response.data.error === "unauthorized") {
                        errorMsg = "Must be logged in to unfollow";
                    } else {
                        errorMsg = "Unexpected error";
                    }
                    alert(errorMsg);
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
