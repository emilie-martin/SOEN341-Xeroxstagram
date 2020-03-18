import React, {useEffect, useState} from 'react';
import Post from "../Post/Post";
import axios from "axios";
import InfiniteScroll from 'react-infinite-scroller';

export default function Feed(props) {
    const [posts, setPosts] = useState([]);
    const [hasMorePosts, setHasMorePosts] = useState(true);
    const [errorMsg, setErrorMsg] = useState("");

    const loadMorePosts = () => {
        const lastPost = posts.length > 0 ? posts[posts.length-1] : 0;
        axios.get(global.config.BACKEND_URL + "/picture/feed?after=" + lastPost).then(
            (response) => {
                const newPosts = response.data;
                if (newPosts && newPosts.length) {
                    setPosts(posts.concat(newPosts));
                } else {
                    setHasMorePosts(false);
                }
            }
        ).catch(
            (error) => {
                setHasMorePosts(false);
                if(error.response && error.response.data && error.response.data.message)
                {
                    setErrorMsg(error.response.data.message);
                }
                else
                {
                    setErrorMsg("An unknown error occurred.");
                }
            }
        )
    }

    useEffect(() => {
        if (props.currentUser) {
            setPosts([]);
            loadMorePosts();
        }
    }, [props.currentUser]);

    return (
        <div>
            {
                props.currentUser &&
                <InfiniteScroll
                    loadMore={loadMorePosts}
                    hasMore={hasMorePosts}
                    initialLoad={false}
                    threshold={0}
                    loader={<div>Loading...</div>}>
                    <div>
                        {
                            posts.map((id) =>
                                <div className="single-post" key={id}>
                                    <Post id={id}/>
                                    <br/>
                                </div>
                            )
                        }
                    </div>
                </InfiniteScroll>
            }
            {errorMsg && <div className="error">{errorMsg}</div>}
        </div>
    );
}