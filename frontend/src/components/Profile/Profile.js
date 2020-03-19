import '../../config';
import './Profile.scss'

import axios from 'axios';
import { Link } from "react-router-dom";
import React, { useState, useEffect } from 'react'

export default function Profile(props) {
    const [username, setUsername] = useState("");
    const [biography, setBiography] = useState("");
    const [displayName, setDisplayName] = useState("");
    const [numFollowers, setNumFollowers] = useState(undefined);
    const [numFollowings, setNumFollowings] = useState(undefined);
    const [numPictures, setNumPictures] = useState(undefined);

    useEffect(() => {
        const loadProfile = () => {
            axios.get(global.config.BACKEND_URL + `/account/profile/${props.username}`).then(
                (response) => {
                    setUsername(response.data.username);
                    setBiography(response.data.biography);
                    setDisplayName(response.data.displayName);
                    setNumFollowers(response.data.numFollowers);
                    setNumFollowings(response.data.numFollowings);
                    setNumPictures(response.data.numPictures);
                }).catch((error) => {
                    console.log(error.response);
                })
        }
        loadProfile();
    }, [props])

    return (
        <div className="profile-container">

            <div>
                <div className="username">
                    <h2>{username} </h2>
                    <div className="display-name">
                        <h2>({displayName})</h2>
                    </div>
                </div>
                <Link to={"/accounts/edit"}>
                    <div className="edit">
                        <button className="edit-btn">
                            <div className="gear-icon">
                                <svg class="bi bi-gear" width="1.5em" height="1.7em" viewBox="0 3 20 7 " fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd" d="M10.837 3.626c-.246-.835-1.428-.835-1.674 0l-.094.319A1.873 1.873 0 016.377 5.06l-.292-.16c-.764-.415-1.6.42-1.184 1.185l.159.292a1.873 1.873 0 01-1.115 2.692l-.319.094c-.835.246-.835 1.428 0 1.674l.319.094a1.873 1.873 0 011.115 2.693l-.16.291c-.415.764.42 1.6 1.185 1.184l.292-.159a1.873 1.873 0 012.692 1.115l.094.319c.246.835 1.428.835 1.674 0l.094-.319a1.873 1.873 0 012.693-1.115l.291.16c.764.415 1.6-.42 1.184-1.185l-.159-.291a1.873 1.873 0 011.115-2.693l.319-.094c.835-.246.835-1.428 0-1.674l-.319-.094a1.873 1.873 0 01-1.115-2.692l.16-.292c.415-.764-.42-1.6-1.185-1.184l-.291.159a1.873 1.873 0 01-2.693-1.115l-.094-.319zm-2.633-.283c.527-1.79 3.064-1.79 3.592 0l.094.319a.873.873 0 001.255.52l.292-.16c1.64-.892 3.434.901 2.54 2.541l-.159.292a.873.873 0 00.52 1.255l.319.094c1.79.527 1.79 3.064 0 3.592l-.319.094a.873.873 0 00-.52 1.255l.16.292c.893 1.64-.902 3.434-2.541 2.54l-.292-.159a.873.873 0 00-1.255.52l-.094.319c-.527 1.79-3.065 1.79-3.592 0l-.094-.319a.873.873 0 00-1.255-.52l-.292.16c-1.64.893-3.433-.902-2.54-2.541l.159-.292a.873.873 0 00-.52-1.255l-.319-.094c-1.79-.527-1.79-3.065 0-3.592l.319-.094a.873.873 0 00.52-1.255l-.16-.292c-.892-1.64.901-3.433 2.541-2.54l.292.159a.873.873 0 001.255-.52l.094-.319z" clip-rule="evenodd"></path>
                                    <path fill-rule="evenodd" d="M10 7.754a2.246 2.246 0 100 4.492 2.246 2.246 0 000-4.492zM6.754 10a3.246 3.246 0 116.492 0 3.246 3.246 0 01-6.492 0z" clip-rule="evenodd"></path>
                                </svg>
                                Edit Profile
                            </div>
                        </button>
                    </div>
                </Link>
            </div>
            <div className="profile-stats">
                <div className="profile-stats-post">
                    {numPictures} posts
                </div>
                <div>
                    {numFollowers} followers
                </div>
                <div className="profile-stats-followings">
                    {numFollowings} followings
                </div>
            </div>

            <div className="profile-bio">
                <div className="profile-bio">
                    <span>{biography}</span>
                </div>
            </div>
        </div>
    )
}