package com.comcast.cdn.traffic_control.traffic_router.core.loc;

import com.comcast.cdn.traffic_control.traffic_router.core.util.CidrAddress;
import com.comcast.cdn.traffic_control.traffic_router.core.util.ComparableTreeSet;

import java.util.Set;

public class FederationMapping implements Comparable<FederationMapping> {
    private final String cname;
    private final int ttl;
    private final ComparableTreeSet<CidrAddress> resolve4 = new ComparableTreeSet<CidrAddress>();
    private final ComparableTreeSet<CidrAddress> resolve6 = new ComparableTreeSet<CidrAddress>();

    public FederationMapping(final String cname, final int ttl, final ComparableTreeSet<CidrAddress> resolve4, final ComparableTreeSet<CidrAddress> resolve6) {
        this.cname = cname;
        this.ttl = ttl;
        this.resolve4.addAll(resolve4);
        this.resolve6.addAll(resolve6);
    }

    public String getCname() {
        return cname;
    }

    public int getTtl() {
        return ttl;
    }

    public ComparableTreeSet<CidrAddress> getResolve4() {
        return resolve4;
    }

    public ComparableTreeSet<CidrAddress> getResolve6() {
        return resolve6;
    }

    @Override
    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.IfStmtsMustUseBraces"})
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final FederationMapping that = (FederationMapping) o;

        if (ttl != that.ttl) return false;
        if (cname != null ? !cname.equals(that.cname) : that.cname != null) return false;
        if (resolve4 != null ? !resolve4.equals(that.resolve4) : that.resolve4 != null) return false;
        return !(resolve6 != null ? !resolve6.equals(that.resolve6) : that.resolve6 != null);
    }

    @Override
    public int hashCode() {
        int result = cname != null ? cname.hashCode() : 0;
        result = 31 * result + ttl;
        result = 31 * result + (resolve4 != null ? resolve4.hashCode() : 0);
        result = 31 * result + (resolve6 != null ? resolve6.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FederationMapping{" +
                "cname='" + cname + '\'' +
                ", ttl=" + ttl +
                ", resolve4=" + resolve4 +
                ", resolve6=" + resolve6 +
                '}';
    }

    // Compare to does not mean that a result of zero means that a.equals(b) is true
    public int compareTo(final FederationMapping other) {
        if (other == null) {
            return -1;
        }

        int result = cname.compareTo(other.cname);
        if (result != 0) {
            return result;
        }

        result = ttl - other.ttl;
        if (result != 0) {
            return result;
        }

        result = resolve4.compareTo(other.resolve4);
        if (result != 0) {
            return result;
        }

        return resolve6.compareTo(other.resolve6);
    }

    public boolean containsCidrAddress(final CidrAddress cidrAddress) {
	    return resolve4.contains(cidrAddress) || resolve6.contains(cidrAddress);
    }

    public ComparableTreeSet<CidrAddress> getResolve4Matches(final CidrAddress cidrAddress) {
        return getResolveMatches(resolve4, cidrAddress);
    }

    public ComparableTreeSet<CidrAddress> getResolve6Matches(final CidrAddress cidrAddress) {
        return getResolveMatches(resolve6, cidrAddress);
    }

    protected ComparableTreeSet<CidrAddress> getResolveMatches(final Set<CidrAddress> resolves, final CidrAddress cidrAddress) {
        final ComparableTreeSet<CidrAddress> cidrAddresses = new ComparableTreeSet<CidrAddress>();

        for (CidrAddress cidrAddressResolve4 : resolves) {
            if (cidrAddressResolve4.includesAddress(cidrAddress)) {
                cidrAddresses.add(cidrAddressResolve4);
            }
        }

        return cidrAddresses;
    }

	public FederationMapping createFilteredMapping(final CidrAddress cidrAddress) {
		return new FederationMapping(cname, ttl, getResolve4Matches(cidrAddress), getResolve6Matches(cidrAddress));
	}
}
