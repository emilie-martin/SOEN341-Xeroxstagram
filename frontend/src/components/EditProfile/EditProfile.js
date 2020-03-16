import React, { useState, useEffect } from 'react'
import './EditProfile.scss'
import Axios from 'axios'
import { Redirect } from 'react-router-dom';

const EditProfile = () => {
    const [loading, setLoading] = useState(true);
    const [displayName, setDisplayName] = useState("");
    const [biography, setBiography] = useState("");
    const [email, setEmail] = useState("");

    useEffect(() => {
        const fetchCurrentUser = () => {
            Axios.get(global.config.BACKEND_URL + "/account").then(
                (response) => {
                    setDisplayName(response.data.displayName);
                    setBiography(response.data.biography);
                    setEmail(response.data.email);
                    setLoading(false);
                }).catch(() => {
                    return <Redirect to="/register"></Redirect>
                })
        }
        fetchCurrentUser();
    }, [loading])

    useEffect(() => {

    }, [displayName, biography, email])

    const handleTyping = (e, setterFunction) => {
        setterFunction(e.target.value);
    }

    const updateProfile = (event) => {
        Axios.put(global.config.BACKEND_URL + `/account/profile/biographyUpdate`,
            {
                "biography": event.target.biography.value
            });

        Axios.put(global.config.BACKEND_URL + `/account/profile/emailUpdate`,
            {
                "email": event.target.email.value
            }).catch((error) => {
                alert(error.response.data.message);
            })

        Axios.put(global.config.BACKEND_URL + `/account/profile/displayNameUpdate`,
            {
                "displayName": event.target.displayName.value
            }).catch((error) => {
                alert(error.response.data.message);
            })
    }
    return (
        <div>
            {
                loading ? 'loading' :
                    <div className="edit-profile-page">
                        <div className="sidebar">
                            <nav class="nav">
                                <ul>
                                    <li class="active"><a href="#!">Instagram++</a></li>
                                    <li><a href="#!">Edit Profile</a></li>
                                    <li><a href="#!">Edit Account</a></li>
                                    <li><a href="#!">Contact Support</a></li>
                                </ul>
                            </nav>
                        </div>
                        <div className="edit-profile">
                            <form className="edit-profile-form" onSubmit={updateProfile}>
                                <h1>Public Profile</h1>
                                <div>
                                    <label>Display Name</label>
                                    <input type="text" name="displayName" value={displayName} onChange={(e) => handleTyping(e, setDisplayName)}></input>
                                </div>
                                <br />
                                <div>
                                    <label>Bio</label>
                                    <textarea name="biography" className="edit-profile-textarea" value={biography} onChange={(e) => handleTyping(e, setBiography)}></textarea>
                                </div>

                                <br />
                                <div>
                                    <label>Email</label>
                                    <input type="text" name="email" value={email} onChange={(e) => handleTyping(e, setEmail)}></input>
                                </div>
                                <div className="edit-profile-button">
                                    <button className="btn">Submit</button>
                                </div>
                                <br />
                            </form>
                        </div>
                    </div>
            }
        </div>
    )
}

export default EditProfile
