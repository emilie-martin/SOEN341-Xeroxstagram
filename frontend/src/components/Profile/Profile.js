import React, { useState, useEffect } from 'react'
import Axios from 'axios';
import '../../config';
import './Profile.scss'
const Profile = (props) => {
    const [username, setUsername] = useState("");
    const [biography, setBiography] = useState("");
    const [displayName, setDisplayName] = useState("");
    const [numFollowers, setNumFollowers] = useState(undefined);
    const [numFollowings, setNumFollowings] = useState(undefined);
    const [numPictures, setNumPictures] = useState(undefined);

    useEffect(() => {
        const loadProfile = () => {
            Axios.get(global.config.BACKEND_URL + `/account/profile/${props.username}`).then(
                (response) => {
                    console.log(response.data);
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
            <table className="profile">
                <tr>
                    <td colSpan="2">
                        <div className="username">
                            <h2>{username} </h2>
                            <div className="display-name">
                                <h2>({displayName})</h2>
                            </div>

                        </div>
                    </td>
                </tr>
                <div className="profile-stats">
                    <tr>
                        <div className="profile-stats-post">
                            <td >
                                {numPictures} posts
                            </td >
                        </div>

                        <td rowSpan="2">
                            {numFollowers} followers
                        </td>
                        <div className="profile-stats-followings">
                            <td rowSpan="2">
                                {numFollowings} followings
                            </td>
                        </div>

                    </tr>
                </div>
                <div className="profile-bio">
                    <tr>
                        <div className="profile-bio">
                            <span>{biography}
                            </span>
                        </div>
                    </  tr>
                </div>


            </table>



        </div>
    )
}

export default Profile;
